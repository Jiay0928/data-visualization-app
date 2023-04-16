package com.example.a1basic

import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableList
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import javafx.stage.Stage
import java.util.concurrent.CompletionStage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

interface IView{
    fun update()
}
class DataSelectBox(private val model:Model):ComboBox<String>(), IView{
    init {
        model.views.add(this)
        value = "one"
        onAction = EventHandler {
            model.setDataSetName(this.value)
        }
        update()
        prefWidth = 160.0
    }
    override fun update() {
        var data = model.getDataKey().toList()
        var childrenSize = items.size
        var dataSize = data!!.size
        for (i in 0 until dataSize){
            if (childrenSize > i){
                items[i] = data[i]
            }else{
                items.add(data[i])
            }

        }
        if (value != model.getDataSetName()){
            value = model.getDataSetName()
        }
//        items.addAll(model.getDataKey())
    }
}

class GraphTypeButton(private val model:Model, private val graphType: String): Button(), IView {
    init{
        model.views.add(this)
        prefWidth = 72.0
        onAction = EventHandler {
            model.setGraphType(graphType)
        }
        text = graphType
        update()
    }
    override fun update() {
        isDisable = model.getGraphType() == graphType
    }
}

class DataContainer(private val model:Model): VBox(), IView{
    init{
        model.views.add(this)
        spacing = 20.0
        update()
    }

    override fun update(){

        val data = model.getData()
        val childrenSize = children.size
        val dataSize = data!!.size
        if(dataSize == 1){
            children.remove(0,children.size)
            children.add(0, DataEntryHBox(model,data[0],0,true))
            return
        }
        for (i in 0 until dataSize){
            if (i < childrenSize){
                val child:DataEntryHBox = children[i] as DataEntryHBox
                child.setValue(data[i])
            }else{
                children.add(i, DataEntryHBox(model,data[i],i,false))
            }

        }
        if (children.size > dataSize){
            children.remove(dataSize,children.size)
        }


    }

}
class LeftPane(private val model:Model): VBox(), IView{
    init{
        model.views.add(this)
        spacing = 10.0
        val label = Label("Dataset name: ${model.getDataSetName()}")
        val dataContainer = DataContainer(model)
        val btn = AddEntryButtonController(model)
        children.addAll(label, dataContainer,btn)


    }

    override fun update() {
        children[0]  = Label("Dataset name: ${model.getDataSetName()}")

    }
}


