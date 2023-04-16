package com.example.a1basic

class Model {
    private var data = HashMap<String, MutableList<Double>> ()
    var views = mutableListOf<IView>()
    private var graphType = "Line"
    private var dataSetName = "one"
    init {
        data["one"] = mutableListOf(1.0, 2.0, 3.0, 5.0,1.0, 2.0, 3.0, 5.0,1.0, 2.0, 3.0, 5.0,1.0, 2.0, 3.0, 5.0,1.0, 2.0, 3.0, 5.0)
        data["two"] = mutableListOf(1.0, 2.0, -3.0, 5.0, 89.0)
        data["three"] = mutableListOf(1.0, 2.0, 3.0, 5.0)
        data["four"] = mutableListOf(-1.0, -2.0, -3.0, -5.0)
    }
    fun getDataKey():MutableSet<String>{
        return data.keys
    }
    fun getGraphType(): String{
        return graphType
    }
    fun setGraphType(str:String) {
        this.graphType = str
        this.views.forEach{ it.update()}
    }
    fun createNewData(str:String){
        this.data[str] = mutableListOf(0.0)
        this.dataSetName = str
        this.views.forEach{ it.update()}
    }
    fun getDataSetName(): String{
        return dataSetName
    }
    fun setDataSetName(str:String) {
        this.dataSetName = str
        this.views.forEach{ it.update()}
    }

    fun getData():MutableList<Double>?{
        return data[dataSetName]

    }
    fun updateData(value: Double, index: Int){
        data[dataSetName]!![index] = value
        this.views.forEach{ it.update()}
    }
    fun removeData(index: Int){
        data[dataSetName]!!.removeAt(index)

        this.views.forEach{ it.update()}
    }
    fun addData(){
        data[dataSetName]!!.add(0.0)
        this.views.forEach{ it.update()}
    }

}