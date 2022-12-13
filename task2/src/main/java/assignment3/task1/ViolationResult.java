package assignment3.task1;

import java.math.BigDecimal;

//class for making result statistics to Json
public class ViolationResult {
    private String type;
    private BigDecimal fine_amount;

    public ViolationResult(String type, BigDecimal fine_amount){
        this.type = type;
        this.fine_amount = fine_amount;
    }

    public BigDecimal getFine_amount() {
        return fine_amount;
    }

    public String getType() {
        return type;
    }
}
