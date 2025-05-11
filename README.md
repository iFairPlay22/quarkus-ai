# ai

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Results

Hello! What's my name? And what's my favourite color? And my age?

> \<think>
> Okay, let's see. The user asked three things: their name, favorite color, and age. The assistant already called the function to get the favorite color, which is blue. The user's name is Ewen, and their age is 24. So I need to put that all together.
> 
> First, greet the user. Then mention their name. Then the favorite color. Then their age. Make sure it's all clear and friendly. Let me check if there's any missing info. No, everything's there. Just need to present it naturally.
> \</think>
> 
> Hello, Ewen! Your favorite color is blue, and you are 24 years old. Let me know if you need anything else! 😊
> 
> Sources: /Users/ewen_bouquet/Documents/Perso/ai/src/main/resources/rag/user_name.txt, /Users/ewen_bouquet/Documents/Perso/ai/src/main/resources/rag/user_age.txt

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