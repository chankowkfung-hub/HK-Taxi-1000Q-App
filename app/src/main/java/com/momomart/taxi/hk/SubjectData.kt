package com.momomart.taxi.hk

data class Chapter(val id:Int, val name:String, val mcq:Int, val lq:Int)
data class Subject(val code:String, val name:String, val icon:String, val chapters:List<Chapter>, val isCore:Boolean=false)
object SubjectData {
    val core = listOf(
        Subject("JIA1","甲1 的士及網約車","🚕", listOf(Chapter(1,"拒載及揀客",120,14),Chapter(2,"計程表及收費",120,14),Chapter(3,"記分及牌照",50,10),Chapter(4,"網約車新規",44,10)), true),
        Subject("JIA2","甲2 地方及路線","🗺️", listOf(Chapter(1,"港島路線",90,10),Chapter(2,"九龍路線",90,10),Chapter(3,"新界及邊境",80,10),Chapter(4,"地標及的士站",73,10)), true),
        Subject("YI","乙 道路使用者守則","🚦", listOf(Chapter(1,"交通燈及標誌",90,10),Chapter(2,"安全距離",90,10),Chapter(3,"迴旋處及讓線",80,10),Chapter(4,"酒精及危險",73,10)), true)
    )
    val electives = listOf<Subject>()
}
