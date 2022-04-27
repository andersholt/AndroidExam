

fun internetIsConnected(): Boolean {
    try {
        var command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    } catch (e: Exception) {
        return false;
    }
}