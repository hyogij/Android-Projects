package com.hyogij.jsonclientmasterdetailview.JsonDatas;

/**
 * A class for User data.
 */
public class User {
    private String id = null;
    private String name = null;
    private String username = null;
    private String email = null;

    private Address address = null;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public class Address {
        private String street = null;
        private String suite = null;
        private String city = null;
        private String zipcode = null;

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        private Geo geo = null;

        public class Geo {
            private String lat = null;
            private String lng = null;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            @Override
            public String toString() {
                return lat + ' ' + lng;
            }
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        @Override
        public String toString() {
            return street + ' ' +
                    suite + ' ' +
                    city + ' ' +
                    zipcode + ' ' +
                    geo.toString();
        }
    }

    private String phone = null;
    private String website = null;

    private Company company = null;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public class Company {
        private String name = null;
        private String catchPhrase = null;
        private String bs = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        @Override
        public String toString() {
            return name + '\'' +
                    catchPhrase + ' ' + bs;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        // Ignore other fields
        return name + ' ' + username + ' ' + email + ' ' +
                address.toString() +
                phone + ' ' +
                website + ' ' +
                company.toString();
    }
}