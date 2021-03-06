/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.patientdiscovery._10.passthru.deferred.response;

import gov.hhs.fha.nhinc.async.AsyncMessageIdExtractor;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.patientdiscovery.passthru.deferred.response.PassthruPatientDiscoveryDeferredRespOrchImpl;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenExtractor;
import java.util.List;
import javax.xml.ws.WebServiceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.ProxyPRPAIN201306UVProxySecuredRequestType;

/**
 * 
 * @author JHOPPESC
 */
public class NhincProxyPatientDiscoveryAsyncRespImpl {

    private Log log = null;

    public NhincProxyPatientDiscoveryAsyncRespImpl() {
        log = createLogger();
    }

    protected Log createLogger() {
        return ((log != null) ? log : LogFactory.getLog(getClass()));
    }

    public MCCIIN000002UV01 proxyProcessPatientDiscoveryAsyncResp(ProxyPRPAIN201306UVProxySecuredRequestType request,
            WebServiceContext context) {
        log.debug("Begin NhincProxyPatientDiscoverySecuredAsyncRespImpl.proxyProcessPatientDiscoveryAsyncResp(Secured)");
        MCCIIN000002UV01 response = null;

        AssertionType assertion = getAssertion(context, null);
        PRPAIN201306UV02 pdRequest = null;
        NhinTargetSystemType targetSystem = null;
        if (request != null) {
            pdRequest = request.getPRPAIN201306UV02();
            targetSystem = request.getNhinTargetSystem();
        }

        response = new PassthruPatientDiscoveryDeferredRespOrchImpl().proxyProcessPatientDiscoveryAsyncResp(pdRequest,
                assertion, targetSystem);

        log.debug("End NhincProxyPatientDiscoverySecuredAsyncRespImpl.proxyProcessPatientDiscoveryAsyncResp(Secured)");
        return response;
    }

    public org.hl7.v3.MCCIIN000002UV01 proxyProcessPatientDiscoveryAsyncResp(
            org.hl7.v3.ProxyPRPAIN201306UVProxyRequestType request, WebServiceContext context) {
        log.debug("Begin NhincProxyPatientDiscoverySecuredAsyncRespImpl.proxyProcessPatientDiscoveryAsyncResp(Unsecured)");
        MCCIIN000002UV01 response = null;

        AssertionType assertion = null;
        PRPAIN201306UV02 pdRequest = null;
        NhinTargetSystemType targetSystem = null;
        if (request != null) {
            pdRequest = request.getPRPAIN201306UV02();
            targetSystem = request.getNhinTargetSystem();
            assertion = request.getAssertion();
        }
        assertion = getAssertion(context, assertion);

        response = new PassthruPatientDiscoveryDeferredRespOrchImpl().proxyProcessPatientDiscoveryAsyncResp(pdRequest,
                assertion, targetSystem);

        log.debug("End NhincProxyPatientDiscoverySecuredAsyncRespImpl.proxyProcessPatientDiscoveryAsyncResp(Unsecured)");
        return response;
    }

    private AssertionType getAssertion(WebServiceContext context, AssertionType oAssertionIn) {
        AssertionType assertion = null;
        if (oAssertionIn == null) {
            assertion = SamlTokenExtractor.GetAssertion(context);
        } else {
            assertion = oAssertionIn;
        }

        // Extract the message id value from the WS-Addressing Header and place it in the Assertion Class
        if (assertion != null) {
            assertion.setMessageId(AsyncMessageIdExtractor.GetAsyncMessageId(context));
            List<String> relatesToList = AsyncMessageIdExtractor.GetAsyncRelatesTo(context);
            if (NullChecker.isNotNullish(relatesToList)) {
                assertion.getRelatesToList().add(AsyncMessageIdExtractor.GetAsyncRelatesTo(context).get(0));
            }
        }

        return assertion;
    }

}
