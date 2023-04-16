package com.example.a1basic

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage


class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val model = Model()
        val toolBar = toolBar(model)
        val leftPane = ScrollPane(LeftPane(model)).apply {
            prefWidth = 300.0
            isFitToHeight = true
            isFitToWidth = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
            style = "-fx-background-color:transparent;";
            padding = Insets(8.0)

        }
        val rightCanvas = RightCanvas(model)
        val rightPane = Pane(rightCanvas).apply {
            prefWidth = 500.0
            background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
            padding = Insets(8.0)
            prefHeightProperty().bind(stage.heightProperty())
            widthProperty ().addListener { obs, oldVal, newVal ->
                rightCanvas.width = newVal.toDouble()
                rightCanvas.update()

            }
            heightProperty ().addListener { obs, oldVal, newVal ->
                rightCanvas.height = newVal.toDouble()
                rightCanvas.update()

            }


        }


        val middleHBox = HBox(leftPane,rightPane).apply {
            spacing = 0.0
            HBox.setHgrow(leftPane, Priority.SOMETIMES)
            HBox.setHgrow(rightPane, Priority.SOMETIMES)
        }
        val vbox = VBox(toolBar,middleHBox)
        stage.title = "CS349 - A2 Graphs - j65cui"
        stage.scene = Scene(vbox, 800.0, 600.0)
        stage.minHeight = 480.0
        stage.minWidth = 640.0
        stage.show()


    }
}

fun main() {
    Application.launch(HelloApplication::class.java)

}

fun toolBar(model: Model):HBox {
    val dataSelectBox = DataSelectBox(model)
    val inputBox = TextField().apply {
        prefWidth = 130.0
        promptText = "Data set name"

    }
    val createButton = CreateControllerButton(model,inputBox)
    val inputHBox = HBox(inputBox, createButton).apply {
        prefWidth = 200.0

    }
    val graphTypeHBox = HBox(GraphTypeButton(model, "Line"),GraphTypeButton(model, "Bar"),GraphTypeButton(model, "Bar(SEM)"),GraphTypeButton(model, "Pie"),GraphTypeButton(model, "Area"))
    val separator1 = Separator()
    val separator2 = Separator()
    separator1.orientation = Orientation.VERTICAL;
    separator2.orientation = Orientation.VERTICAL;

    val hBox = HBox(dataSelectBox, inputHBox, graphTypeHBox)
    hBox.apply {
        spacing = 32.0
        alignment = Pos.CENTER_LEFT
        style = "-fx-border-color : lightgrey; -fx-border-width : 0 0 1 0 ";
    }
    hBox.children.add(1, separator1)
    hBox.children.add(3, separator2)
    return hBox

}