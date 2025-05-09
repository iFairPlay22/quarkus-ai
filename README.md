# ai

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Results

Hello! What's my name? And what's my favourite color?


> \<think>
> Okay, let's see. The user asked,
> "Hello! What's my name? And what's my favourite color?" And they mentioned that their name is Ewen. So first, I need to answer both parts of the question.
> 
> Wait, the name part is straightforward. They told me their name is Ewen, so I can just say that. But the favorite color part requires using the function. I called the favouriteColour function with the username "Ewen" and got the response "blue". So I should relay that information back.
> 
> I need to make sure I address both questions: confirming their name and stating their favorite color. Let me structure the response clearly. Maybe start with a greeting, mention the name, and then the color. Keep it friendly and concise.
> \</think>
> 
> --> Hello, Ewen! Your favorite color is blue. 😊 <--

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```