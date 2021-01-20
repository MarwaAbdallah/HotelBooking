# Hotel Booking System

### Functionnalities :
- Choose a location for staying, see all hotels at this location, make a reservation
- Authentication and Role based access (different dashboard views based on roles)

### Roles
1- Anonymous : Can browse reservation (but not make one - requires sign in or sign up).<br/>
2- Customer : can make/view/edit/cancel reservations.<br/>
3- Hotel Employee : Can view/cancel all reservations for the Hotel they are employed in<br/>
4 - Administrator : User + user role management, add/remove hotels.<br/>

### Stack
- Servlet, JSP (MVC)
- Postgresql DB

### Basic System Diagram

![Picture for Servlet MVC architecture](WebContent/img/HotelBooking.jpg?raw=true "Servlet MVC architecture")


### Screenshots
#### Home Page
<img width="800" alt="portfolio_view" src="WebContent/img/home.png">

#### Choose a Location, get list of hotels for that location

<img  alt="show hotels in location" src="WebContent/img/show_hotels_in_Location.png">

#### Reserve : First login or sign in before completing reservation
Customer can sign in, or if they don't have an account, sign up.
It's a necessary step to proceed with reservation
<img  alt="Reserve " src="WebContent/img/reserve-1.png">

#### Reserve : Sign In (or Sign Up)
Customer can sign in, or if they don't have an account, sign up.
It's a necessary step to proceed with reservation
<img  alt="Sign In" src="WebContent/img/signin.png">

#### Customer Home Page
Customer home page accessible only for users with Role=App user 
as defined in the servlet-security section of pom.xml
<img  alt="Customer Home" src="WebContent/img/customer_home_page.png">

#### Hotel Employee Home Page
Hotel Employee home page accessible only for users with Role=Hotel Manager,
as defined in the servlet-security section of pom.xml

The Hotel Employee for Hotel X can see all reservations, made by any user, for this hotel.
<img  alt="Admin Home" src="WebContent/img/admin_home.png">

#### Admin
Admin Home Page : Admin can add / edit / delete application users with a role chosen by admin.
Admin can also add / edit / delete hotels that are listed in the booking app
accessible only for users with Role=Golab Admin,
as defined in the servlet-security section of pom.xml

<img  alt="Admin Home" src="WebContent/img/admin_home.png">


#### Admin "User Management" Home page
Add/ edit / delete user
<img  alt="Admin User Mgmt Page" src="WebContent/img/admin_manage_user.png">

