import GameObserver
interface GameObservable {
    fun addObserver(observer: GameObserver)
    fun removeObserver(observer: GameObserver)
    fun notifyObservers(event: String)
}