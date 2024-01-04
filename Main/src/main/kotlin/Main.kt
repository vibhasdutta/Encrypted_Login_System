import java.io.File
import java.util.Scanner
import java.io.FileWriter
import java.io.BufferedWriter
import java.nio.file.Path

fun main() {
    val input = Scanner(System.`in`)
    var org = "vibhas_password_manager" // Change organization name if needed

    // reading csv file
    val csvFilePath = "D:\\coding\\KOTLIN\\DATABASE.csv"
    val csvFile = File(csvFilePath)
    if (!csvFile.exists()) {
        println("File not found: $csvFilePath")
        return
    }
    try {
        val lines = csvFile.readLines()

        for (line in lines) {
            val columns = line.split(",")

            // Process the columns as needed
            for (column in columns) {

            }
        }
        println("Database connected.....")
    } catch (e: Exception) {
        println("Error reading the CSV file: ${e.message}")
    }


    // printing banner
    for(i in 1..5){
        for(j in 1..30){
            if(i==3&&j==1 || i==3&&j==30){
                print("")
            }
            else if (i==1|| i==5 || j==1 || j==30 ){
                print("*")
            }
            else if(i==3&&j==5){
                print(org)
            }
            else{
                print(" ")
            }
        }
        println("")
    }

    // login and signup page
    println("pls enter\n1 -> Login\n2 -> Signup")
    val login = UserLogindefault()
    val welcome = Welcomeuser()
    var conditioncheck = true
    var choice = input.nextInt()

    if (choice==1){
        println("login page")                    // LOGIN PAGE    <--------------------------
        login.userLogin(csvFile,csvFilePath)
        welcome.welcomeUser()
    }else{
        println("Signup page")                     // SIGNUP PAGE    <--------------------------
        login.userSignup(csvFile,csvFilePath)
        welcome.welcomeUser()
    }
}



open class UserLogindefault{
    val input = Scanner(System.`in`)
    var userName: String=""
    var userPassword : String=""
    var conditioncheck=true

fun userLogin(csvFile:File,csvFilePath:String) {           // ==========userloginFunction

    while (conditioncheck) {
            print("enter username : ")
            userName = input.nextLine()
            print("enter password : ")
            userPassword = input.nextLine()
        var encrptPassword= encrption(userPassword)
            try {
                val users = readUsersFromCsv(csvFile)

                // Let's assume the user enters login data
                val enteredName = userName
                val enteredPassword = encrptPassword

                // Check if the entered credentials match
                if (checkCredentials(users, enteredName, enteredPassword)) {
                    println("Login successful.")
                    conditioncheck = false
                } else {
                    println("Login failed. Invalid username or password.")
                    println("Hey their want to Signup! not done yet press -> 1 ")
                    val choose = input.nextInt()
                    if (choose == 1) {
                        input.nextLine() // for  new line
                        userSignup(csvFile, csvFilePath)
                    }

                }
            } catch (e: Exception) {
                println("Error reading the CSV file: ${e.message}")
            }
        }
    }

fun userSignup(csvFile:File,csvFilePath:String){
    while(conditioncheck) {
        print("enter username : ")
        userName= input.nextLine()
        print("enter password : ")
        userPassword= input.nextLine()
            var encrptPassword= encrption(userPassword)
            try {
                //reading existing user file
                val users = readUsersFromCsv(csvFile)

                // the user enters new data
                val newName = userName
                val newPassword = encrptPassword

                // Check if a user with a similar name already exists (ignoring case sensitivity and spacing)
                if (!userExists(users, newName)) {
                    // Add the new user to the list
                    val newUser = User(newName, newPassword)
                    users.add(newUser)

                    // append the new data to the CSV file
                    BufferedWriter(FileWriter(csvFilePath, true)).use { writer ->
                        writer.appendln("$newName,$newPassword")
                    }
                    conditioncheck=false
                    println("Data appended successfully.")
                } else {
                    println("User with a similar name already exists.")
                }
            } catch (e: Exception) {
                println("Error reading/appending the CSV file: ${e.message}")
            }
        }
    }
}
data class User(val name: String, val password: String )

class Welcomeuser:UserLogindefault() {                    // ============welcome function
    fun welcomeUser() {
        println("welcome user $userName")
        var inputcontinuation = true
       while(inputcontinuation){
           println("Add Password -> 1\nRemove Password -> 2\nView PasswordList -> 3\nExit -> 4")
           val choice2 = input.nextInt()
           when (choice2) {
               1 -> ""
               2 -> ""
               3 -> ""
               4 -> {println("exiting..")
                    inputcontinuation = false
               }
               else -> println("wrong selection")
           }
       }
    }
}

fun readUsersFromCsv(file: File): MutableList<User> {
    val users = mutableListOf<User>()

    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val columns = line.split(",")

            if (columns.size == 2) {
                val name = columns[0].trim()
                val password = columns[1].trim()

                val user = User(name, password)
                users.add(user)
            } else {
                println("Invalid line format: $line")
            }
        }
    }
    return users
}

fun userExists(users: List<User>, nameToCheck: String): Boolean {
    return users.any { it.name.trim().equals(nameToCheck.trim(), ignoreCase = true) }
}

fun checkCredentials(users: List<User>, enteredName: String, enteredPassword: String): Boolean {
    val matchingUser = users.find { it.name.trim().equals(enteredName.trim(), ignoreCase = true) }

    return matchingUser?.password?.trim() == enteredPassword.trim()
}

//----------->ENCRYPTION ALGORITHM<-------------\\
fun encrption(textMsg:String):String{

    var textarr = textMsg.toCharArray()
    var newText = ""
    for(i in textarr){

        var tnum = i.toInt() + 50

        newText+= tnum.toChar()
    }
    return newText
}
fun decryption(newText:String) {

    var orignalText = ""
    var newtextarr = newText.toCharArray()
    for(i in newtextarr){
        var tnum = i.toInt() -50
        orignalText +=tnum.toChar()
    }
    print(orignalText)
}