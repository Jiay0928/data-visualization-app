package com.example.a1basic

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

class RightCanvas(private val model:Model): Canvas(500.0,550.0), IView{
    init{
        model.views.add(this)
        update()
    }

    override fun isResizable(): Boolean {
        return true
    }
    private fun drawLineGraph(){
        width = this.width
        height = this.height
        val data = model.getData()
        if (data!!.size == 1){
            graphicsContext2D.apply {
                fill = Color.RED
                strokeText(data[0].toString(),width/2,height/2)
                fillOval(width/2, height/2, 8.0,8.0)

            }
            return
        }
        val min = data!!.minOrNull()!!
        val dif = data!!.maxOrNull()!! - min
        val count = data.size
        val wInterval = (width - 40)/(count-1)
        var hInterval = (height - 40)/dif
        if (min == data!!.maxOrNull()){
            hInterval = (height-20)/2
            graphicsContext2D.apply {
                val xpoint = mutableListOf<Double>()
                val ypoint = mutableListOf<Double>()
                for (i in 0 until data.size){
                    xpoint.add(10+ i*wInterval)
                    ypoint.add (hInterval)

                }
                strokePolyline(xpoint.toDoubleArray(),ypoint.toDoubleArray(),data.size)
                for (i in 0 until data.size){
                    fill = Color.RED
//                    enhancement: show more statistic by adding data to line graph
                    strokeText(data[i].toString(),10+ i*wInterval-4,10+ i*wInterval-4 )
                    fillOval(10+ i*wInterval-4, hInterval-4, 8.0,8.0)
                }
            }
            return
        }

        graphicsContext2D.apply {
            val xpoint = mutableListOf<Double>()
            val ypoint = mutableListOf<Double>()
            for (i in 0 until data.size){
                xpoint.add(10+ i*wInterval)
                ypoint.add (height - (10 + hInterval * (data[i]-min)))

            }
            strokePolyline(xpoint.toDoubleArray(),ypoint.toDoubleArray(),data.size)
            for (i in 0 until data.size){
                fill = Color.RED
                strokeText(data[i].toString(),10+ i*wInterval-4,height - (10 + hInterval * (data[i]-min))-4 )
                fillOval(10+ i*wInterval-4, height - (10 + hInterval * (data[i]-min))-4, 8.0,8.0)
            }
        }
    }
//    enhancement: AreaGraph
    private fun drawAreaGraph(){
        width = this.width
        height = this.height
        val data = model.getData()
        var min = data!!.minOrNull()!!

        if (min > 0){
            min = 0.0
        }
        var max = data.maxOrNull()!!
        if (max < 0){
            max = 0.0
        }
        val dif = max - min

        val count = data.size
        val wInterval = (width - 20)/(count * 2-2)
        val hInterval = (height - 20)/ kotlin.math.max(kotlin.math.max(abs(min), abs(max)), dif)

        graphicsContext2D.apply {
            val xpoint = mutableListOf<Double>()
            val ypoint = mutableListOf<Double>()
            xpoint.add(10.0)
            ypoint.add(height - (10 + hInterval * (0-min)))
            for (i in 0 until data.size){

                xpoint.add(10+ 2*i*wInterval)
                ypoint.add(height - (10 + hInterval * (data[i]-min)))

            }
            xpoint.add(10 + 2 * (data.size-1) * wInterval)
            ypoint.add(height - (10 + hInterval * (0-min)))

            fill = Color.LIGHTGRAY
            fillPolygon(xpoint.toDoubleArray(),ypoint.toDoubleArray(), data.size + 2)
            strokeLine(0.0, height - (10 + hInterval * (0-min)), width, height - (10 + hInterval * (0-min)))

        }
    }
    private fun drawBarGraph(){
        width = this.width
        height = this.height
        val data = model.getData()
        var min = data!!.minOrNull()!!

        if (min > 0){
            min = 0.0
        }
        var max = data.maxOrNull()!!
        if (max < 0){
            max = 0.0
        }
        val dif = max - min

        val count = data.size
        val wInterval = (width - 20)/(count * 2-1)
        val hInterval = (height - 20)/ kotlin.math.max(kotlin.math.max(abs(min), abs(max)), dif)

        graphicsContext2D.apply {

            for (i in 0 until data.size){
                val colorInt = (255.0 / (data.size + 1) * i).toInt()
                fill = Color.rgb(colorInt, colorInt, 200)
                if (data[i] > 0){
                    fillRect(10+ 2*i*wInterval, height - (10 + hInterval * (data[i]-min)), wInterval,data[i]*hInterval)
                }else{
                    fillRect(10+ 2*i*wInterval, height - (10 + hInterval * (0-min))
                        , wInterval,-data[i]*hInterval)

                }

            }
            strokeLine(0.0, height - (10 + hInterval * (0-min)), width, height - (10 + hInterval * (0-min)))
        }
    }
    private fun seCalculator(lst:MutableList<Double>, mean:Double):Double{
        var res = 0.0
        for( i in 0 until lst.size){
            res += Math.pow(lst[i] - mean, 2.0)


        }
        res /= lst.size
        res = sqrt(res)
        return res/ sqrt(lst.size.toDouble())


    }
    private fun drawSEMBarGraph(){
        width = this.width
        height = this.height
        val data = model.getData()
        var min = data!!.minOrNull()!!

        if (min < 0){
            return
        }else{
            min = 0.0
        }
        var max = data.maxOrNull()!!

        val dif = max - min

        val count = data.size
        val wInterval = (width - 20)/(count * 2-1)
        val hInterval = (height - 20)/ kotlin.math.max(kotlin.math.max(abs(min), abs(max)), dif)

        graphicsContext2D.apply {
            for (i in 0 until data.size){
                val colorInt = (255.0 / (data.size + 1) * i).toInt()
                fill = Color.rgb(200, colorInt, colorInt)
                if (data[i] > 0){
                    fillRect(10+ 2*i*wInterval, height - (10 + hInterval * (data[i]-min)), wInterval,data[i]*hInterval)
                }else{
                    fillRect(10+ 2*i*wInterval, height - (10 + hInterval * (0-min))
                        , wInterval,-data[i]*hInterval)

                }

            }
            strokeLine(0.0, height - (10 + hInterval * (0-min)), width, height - (10 + hInterval * (0-min)))
            val median = data.sum()/data.size
            strokeLine(0.0, height - (10 + hInterval * (median-min)), width, height - (10 + hInterval * (median-min)))
            val se = seCalculator(data,median)
            val se1 = median + se
            val se2 = median - se
            setLineDashes(25.0)
            strokeLine(0.0, height - (10 + hInterval * (se1-min)), width, height - (10 + hInterval * (se1-min)))
            strokeLine(0.0, height - (10 + hInterval * (se2-min)), width, height - (10 + hInterval * (se2-min)))
            setLineDashes(0.0)
            strokeText("Mean: $median", 20.0,30.0, 60.0)
            val seString = String.format("%.3f", se)
            strokeText("SEM: $seString", 20.0,50.0, 60.0)
        }
    }
    private fun drawPieGraph() {
        width = this.width
        height = this.height
        val data = model.getData()
        var min = data!!.minOrNull()!!
        if (min < 0) {
            return
        }
        val sum = data.sum()
        var startAngel = 0.0
        graphicsContext2D.apply {
            for (i in 0 until data.size) {
                val colorInt = (255.0 / (data.size + 1) * i).toInt()
                fill = Color.rgb(255-colorInt, 0, colorInt)
                val radius = min(width, height) -20
//                val arc = arc(width / 2, height / 2, radius, radius, startAngel, 30.0)
                val newAngel = data[i]/sum * 360

                fillArc(10.0, 10.0, radius, radius, startAngel, newAngel, ArcType.ROUND)
                startAngel += newAngel



            }

        }
    }
    override fun update() {
        graphicsContext2D.clearRect(0.0, 0.0, width, height)
        if (model.getGraphType() == "Line"){
            drawLineGraph()
        }
        if (model.getGraphType() == "Bar"){
            drawBarGraph()
        }
        if (model.getGraphType() == "Bar(SEM)"){
            drawSEMBarGraph()
        }
        if (model.getGraphType() == "Pie"){
            drawPieGraph()
        }
        if (model.getGraphType() == "Area"){
            drawAreaGraph()
        }
    }

}

