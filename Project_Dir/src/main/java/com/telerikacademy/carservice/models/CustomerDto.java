package com.telerikacademy.carservice.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

    public class CustomerDto {
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$",
                message = "Please provide a valid email address!")
        @Size(
                min = 5,
                max = 255,
                message = "The customer email address length must be min {min} characters and max {max} characters!")
        private String email;

        @NotNull
        @Size(
                min = 5,
                max = 60,
                message = "The customer name length must be min {min} characters and max {max} characters!")
        private String name;

        @NotNull
        @Pattern(regexp = "[0-9+]*",
                message = "The customer phone number must contain only digits!")
        @Size(
                min = 5,
                max = 30,
                message = "The customer phone number length must be min {min} characters and max {max} characters!")
        private String phone;


        public CustomerDto() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
