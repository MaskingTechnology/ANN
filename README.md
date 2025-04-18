# Artificial Neural Network (ANN)

This project is our own implementation of a simple feedforward, fully connected neural network for number classification.

**Note**: This is a fork of an older project we originally created in 2015. In this fork, we've made some improvements but haven't modernized the codebase.

## Project setup

This is a Maven (Java) project currently developed using the IntelliJ IDE.

In addition to the source folder, there's a `data` folder containing two subfolders

1. **input** - Training data (IDX files containing vectors of handwritten numbers)
2. **output** - Created models (serialized Java objects)

## Installation

Clone the repository and install the Maven dependencies.

## Running the application

This project contains two applications: one for creating and training models, and another for using the trained models.

### Creating and training model

In the `nl.uitdehoogte.ann` root package, you'll find the `Main` class containing the logic for both tasks. Since this is just a playground for us, there are no CLI options or configurations. If you want to experiment, simply modify the code and run the class.

### Using created models

We've created a simple UI for drawing and recognizing numbers. Its main `Controller` class can be found in the `nl.uitdehoogte.ann.ui` package.
As with the `Main` class, you'll need to manually set the desired model in the code before starting the app.

Once started, you can draw on the left using the primary mouse button. Then, click the **Execute** button to see which number the model predicts.

To clear the drawing, simply right-click on the canvas (i.e., use the secondary mouse button).

To test the model against the verification test set, you can use the (almost self-descriptive) **Read Next** button. This will randomly pick a sample from the test set, draw it on the canvas, and automatically click the **Execute** button.