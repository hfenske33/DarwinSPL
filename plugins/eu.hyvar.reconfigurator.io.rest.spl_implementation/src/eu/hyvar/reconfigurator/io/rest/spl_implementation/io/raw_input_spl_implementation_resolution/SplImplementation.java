
package eu.hyvar.reconfigurator.io.rest.spl_implementation.io.raw_input_spl_implementation_resolution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SplImplementation {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("signature_id")
    @Expose
    private String signatureId;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("signature_model")
    @Expose
    private SignatureModel_ signatureModel;

    /**
     * 
     * (Required)
     * 
     */
    public String getSignatureId() {
        return signatureId;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SignatureModel_ getSignatureModel() {
        return signatureModel;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setSignatureModel(SignatureModel_ signatureModel) {
        this.signatureModel = signatureModel;
    }

}
