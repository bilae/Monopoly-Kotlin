interface Observer {
    fun update();
}
interface GameObserver {
    fun onGameEvent(event: String)
}