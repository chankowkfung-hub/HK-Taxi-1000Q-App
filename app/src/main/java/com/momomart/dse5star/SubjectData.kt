package com.momomart.dse5star
data class Chapter(val id:Int, val name:String, val mcq:Int, val lq:Int)
data class Subject(val code:String, val name:String, val icon:String, val chapters:List<Chapter>, val isCore:Boolean=false)
object SubjectData {
    val core = listOf(
        Subject("CHI","中文","文", listOf(Chapter(1,"閱讀",200,50),Chapter(2,"寫作",0,80)), true),
        Subject("ENG","英文","EN", listOf(Chapter(1,"Reading",300,0),Chapter(2,"Writing",0,100)), true),
        Subject("MATH","數學","∑", listOf(Chapter(1,"Core",400,100)), true)
    )
    val electives = listOf(
        Subject("ECON","經濟","📊", (1..12).map{ Chapter(it,"ECON Ch"+it,150,20) }),
        Subject("BAFS","企會財","💼", (1..10).map{ Chapter(it,"BAFS C"+it,120,15) }),
        Subject("PHY","物理","⚛", (1..9).map{ Chapter(it,"PHY C"+it,180,25) }),
        Subject("CHEM","化學","🧪", (1..11).map{ Chapter(it,"CHEM C"+it,160,22) }),
        Subject("BIO","生物","🧬", (1..13).map{ Chapter(it,"BIO C"+it,140,18) }),
        Subject("M2","M2","📈", (1..8).map{ Chapter(it,"M2 C"+it,200,30) }),
        Subject("ICT","ICT","💾", (1..10).map{ Chapter(it,"ICT C"+it,130,20) }),
        Subject("GEOG","地理","🌐", (1..9).map{ Chapter(it,"GEOG C"+it,110,15) }),
        Subject("HIST","歷史","📜", (1..12).map{ Chapter(it,"HIST C"+it,100,25) })
    )
}