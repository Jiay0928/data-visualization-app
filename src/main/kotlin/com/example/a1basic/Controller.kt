package com.example.a1basic

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class CreateControllerButton(model: Model, textField: TextField): Button("Create")  {
    init{
        onAction = EventHandler {
            if (textField.text != ""){
                model.createNewData(textField.text)
                textField.text = ""
            }
        }
    }
}

class DataEntryHBox(private var model:Model, private var value: Double, private val index:Int, isDisable:Boolean):HBox(){
    lateinit var textField:TextField
    init{
        val label = Label("Entry #$index").apply {
            minWidth = 40.0
        }
        textField = TextField().apply {
            text = value.toString()
            minWidth = 160.0
            textProperty().addListener{
                    _, oldValue, newValue ->
                text = newValue
                if (oldValue != newValue){
                    val douNewValue = newValue.toDoubleOrNull()
                    if (douNewValue != null && newValue[0] != '.' && newValue[newValue.length-1] != '.'){
                        model.updateData(douNewValue!!,index)
                    }
                }
            }
        }
        val button = Button("X").apply {
            maxWidth = 20.0
            onAction = EventHandler {
                print(index)
                model.removeData(index)
            }
            if (isDisable){
                setDisable(true)
            }
        }
        children.addAll(label,textField,button)
        spacing = 8.0
        alignment = Pos.CENTER_LEFT
        HBox.setHgrow(button, Priority.SOMETIMES)
        HBox.setHgrow(label, Priority.SOMETIMES)
        HBox.setHgrow(textField, Priority.SOMETIMES)
    }

    fun setValue(value:Double){
        textField.text = value.toString()

    }
}
class AddEntryButtonController(model: Model): Button("Add Entry"){
    init{
        onAction = EventHandler {
            model.addData()
        }
        maxWidth = Double.MAX_VALUE
    }
}