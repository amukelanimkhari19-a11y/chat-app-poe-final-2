# QuickChat

QuickChat is a small interactive Java console application for the PROG5121/PROG POE. It demonstrates user registration and login, input validation, and composing messages that can be sent, stored, or disregarded.

## Features

- Registers a user with a validated username and password, then checks login credentials.
- Validates South African cell numbers in international format (`+27` followed by 1–10 digits).
- Creates messages with an ID and a hash derived from the ID, message number, and first and last words.
- Checks the recipient number and limits message text to 250 characters.
- Sends messages during the current run, disregards them, or appends stored messages to `messages.json`.
- Includes JUnit 5 tests for registration, login, and message behavior.

## Requirements

- Java Development Kit (JDK) 25
- Apache Maven

The Maven compiler source and target are both set to Java 25. The GitHub Actions workflow also runs tests with JDK 25.

## Build and test

Run these commands from the project root:

```sh
mvn test
```

To build the project and run its tests:

```sh
mvn package
```

## Run the application

After compiling the project, start the console application with:

```sh
java -cp target/classes Main
```

The program first asks for registration details, then asks you to log in with the registered username and password. After a successful login, enter how many messages you want to process and choose an option from the menu.

### Registration rules

- **Username:** must contain `_` and be no longer than five characters. For example, `sam_1`.
- **Password:** must contain at least eight characters, an uppercase letter, a digit, and a special character. For example, `Ch&&sec@ke99!`.
- **Cell number:** must start with `+27` and contain 1–10 digits after the country code. For example, `+27838968976`.

The cell number check reports its result separately from user registration. A failed username or password check prevents the credentials from being registered, so login will not succeed.

### Message flow

After login:

1. Enter the number of messages to process.
2. Choose **1) Send Messages** and enter a recipient number and message text for each message.
3. Choose `send`, `store`, or `disregard` when prompted.
   - `send` counts the message as sent during the current run and prints its details.
   - `store` appends the message data to `messages.json` in the current working directory.
   - `disregard` does not send or store the message.
4. Choose **2) Show recently sent messages** or **3) Quit** from the menu.

For example, valid message text could be `Hi Mike, can you join us for dinner tonight?`; a valid recipient could be `+27718693002`.

## Project structure

```text
.
├── pom.xml
├── src
│   ├── main/java
│   │   ├── Main.java       # Console prompts and application flow
│   │   ├── Login.java      # Registration and login validation
│   │   └── Message.java    # Message validation, hashes, and storage
│   └── test/java
│       ├── LoginTest.java
│       └── MessageTest.java
└── .github/workflows
    └── testjava.yml        # Runs Maven tests in GitHub Actions
```

## Current limitations

- **Show recently sent messages** currently displays `Coming Soon.`; although sent messages are tracked in memory, this menu option does not display them.
- Recipient and message-length validation results are displayed, but invalid values do not prevent the user from choosing to send or store a message.
- Stored messages are appended as one JSON object per line. The application does not currently load or display stored messages.
- User credentials and message counts are kept in memory and are not persistent between application runs.

## Contributing

Contributions are welcome. To propose a change:

1. Create a branch for your change.
2. Keep changes focused and follow the existing Java and Maven conventions.
3. Add or update JUnit tests for behavior changes.
4. Run `mvn test` and ensure it passes.
5. Open a pull request describing the change and any relevant test results.

## License

No license is currently specified in this repository. Contact the project maintainers before redistributing or reusing the code.
