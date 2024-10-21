public class Enums {
    public enum CurrencyType {
        EUR(1.0),
        USD(0.84),
        GBP(1.16);

        private final double conversionRate;

        CurrencyType(double conversionRate){
            this.conversionRate = conversionRate;
        }


        public double getConversionRate(){
            return conversionRate;
        }

    }

    public enum TransactionType {TRANSFER_TO, TRANSFER_FROM, WITHDRAW, DEPOSIT}
}
