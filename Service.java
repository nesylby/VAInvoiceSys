public class Service {

        private int service_id;
        private String service_name;
        private double hourly_rate;
    
        public Service(int service_id, String service_name, double hourly_rate) {
            this.service_id = service_id;
            this.service_name = service_name;
            this.hourly_rate = hourly_rate;
        }
    
        public int getServiceId() {
            return service_id;
        }
    
        public void setServiceId(int service_id) {
            this.service_id = service_id;
        }
    
        public String getServiceName() {
            return service_name;
        }
    
        public void setServiceName(String service_name) {
            this.service_name = service_name;
        }
    
        public double getHourlyRate() {
            return hourly_rate;
        }
    
        public void setHourlyRate(double hourly_rate) {
            this.hourly_rate = hourly_rate;
        }
    
        @Override
        public String toString() {
            return "Service{" +
                    "serviceId=" + service_id +
                    ", serviceName='" + service_name + '\'' +
                    ", hourlyRate=" + hourly_rate +
                    '}';
        }
    }
    
