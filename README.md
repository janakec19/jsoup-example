
# AutomateAuthenticator

Automate the login process on the Polar Flow website using Java, HttpURLConnection, and Jsoup.



## Prerequisites

- Java Development Kit (JDK) installed
- Active Polar Flow account https://flow.polar.com/register

## How To Run

- Download the repo to your machine.
- Open the project in your preferred Java IDE.
- Download required dependencies with Maven.
- Replace the placeholder values in the Main.java file with your actual email and password:
```java
String postParams = "email=your-email@example.com&password=your-password&csrfToken=" + csrfToken;
```
- Save the changes and run the 'Main' class.





## Description
This Java program automates the login process on the Polar Flow website through the following steps:


- Sends a GET request to the Polar Flow login page to retrieve the CSRF token.
- Extracts the CSRF token from the response.
- Sends a POST request to the login page with the provided credentials and CSRF token.
- If the login is successful (HTTP OK), sends a GET request to the user's diary page.
- Extracts and prints the title and username from the diary page.
**Note:** Please use your actual email and password in the Main.java file for proper functionality.
## Maven Dependdencies

```
    <!-- Jsoup -->
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.17.2</version>
    </dependency>
```
## License

This project is licensed under the MIT License - see the  [LICENSE](https://choosealicense.com/licenses/mit/) file for details

