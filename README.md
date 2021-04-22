# hotelBooking-REST
Hotel booking rest service

Specifications
jdk: 1.8
framework: Spring boot 2.4.5
database: H2
  
This rest api follows rest conventions and supports HATEOS.
This api is secured with JWT bearer token authentication mechanism.
All queries are embeded in the application itself and schema initialization is automated with hibernanates hb2dll.

Please find included postman collection for endpoint info.

3 users are preloaded for demonstation.

Implements crud operation for hotel, searching hotel on various criterion like rating, location, availability etc.

Allows user to book a hotel.
once hotel is booked user can add a review of their stay.

rating will be calculated by the average of all ratings for that hotel.

number of available rooms is calculated by total number of rooms and booked rooms for specified dates.
