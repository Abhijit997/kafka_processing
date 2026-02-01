package com.example.kafkabridge.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private String first_name;
    private String last_name;
    private String phone_no;
    private String address_line_1;
    private String zip;
    private boolean anonymize_flag;

    public void anonymize() {
        if (this.anonymize_flag) {
            this.first_name = "****";
            this.last_name = "****";
            this.phone_no = "0000000000";
            this.address_line_1 = "****";
            this.zip = "00000";
        }
    }
}