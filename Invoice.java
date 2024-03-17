public class Invoice {

        private int invoice_id;
        private int client_id;
        private String date;
        private double total_amount;
        private String status;
    
        public Invoice(int invoice_id, int client_id, String date, double total_amount, String status) {
            this.invoice_id = invoice_id;
            this.client_id = client_id;
            this.date = date;
            this.total_amount = total_amount;
            this.status = status;
        }
    
        public int getInvoiceId() {
            return invoice_id;
        }
    
        public void setInvoiceId(int invoice_id) {
            this.invoice_id = invoice_id;
        }
    
        public int getClientId() {
            return client_id;
        }
    
        public void setClientId(int client_id) {
            this.client_id = client_id;
        }
    
        public String getDate() {
            return date;
        }
    
        public void setDate(String date) {
            this.date = date;
        }
    
        public double getTotalAmount() {
            return total_amount;
        }
    
        public void setTotalAmount(double total_amount) {
            this.total_amount = total_amount;
        }
    
        public String getStatus() {
            return status;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }
    
        @Override
        public String toString() {
            return "Invoice{" +
                    "invoiceId=" + invoice_id +
                    ", clientId=" + client_id +
                    ", date='" + date + '\'' +
                    ", totalAmount=" + total_amount +
                    ", status='" + status + '\'' +
                    '}';
        }
    }