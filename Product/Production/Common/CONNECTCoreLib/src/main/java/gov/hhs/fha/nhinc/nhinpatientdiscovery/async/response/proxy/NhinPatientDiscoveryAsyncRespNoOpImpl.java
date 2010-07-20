/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.nhinpatientdiscovery.async.response.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201306UV02;

/**
 *
 * @author JHOPPESC
 */
public class NhinPatientDiscoveryAsyncRespNoOpImpl implements NhinPatientDiscoveryAsyncRespProxy {

    public MCCIIN000002UV01 respondingGatewayPRPAIN201306UV02(PRPAIN201306UV02 body, AssertionType assertion, NhinTargetSystemType target) {
        return new MCCIIN000002UV01();
    }

}