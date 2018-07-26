#Standard Guide
N.B. Please feel free to add any other standard 

These are the basic rules/standards we will adhere to. This will also help us to have a professional and easy to read code style.

    1. Naming convention
            i) N.B. classes, methods, variables and constants must be descriptive and unambiguous
            ii) N.B. constant variable must be uppercase
e.g. the days in the year
int final DAYS_IN_YEAR = 365
            iii) Pascal casing for classes
Capitalize the first letter of each word in a class name
e.g. First letter of every word is uppercase
ComputerArea, Math, and JOptionPane
            iv) Camel casing methods and variable identifiers
e.g. first letter of the first word is lowercase
	userName, accountNumber and physicalAddress
            v) Components variable names you always precede with three letters of the component
e.g. button that a user clicks for sending.
btnSend
e.g. text fields that accept name and surname
		txtName
		txtSurname
       e.g. radio buttons for yes or no
		rdbYes
		rdbNo

    2. Documentation
            i) Please add comments to your methods, variables and classes and be as clear and as concise as possible
            ii) Include a summary at the beginning of the program to explain what the program does, its key features, and any unique techniques it uses
            iii) Single line comment //text...
            iv) Multiple/Paragraph comments /*...text...*/
            v) javadoc comments. javadoc comments begin with /** and end with */. They can be extracted into an HTML file using JDK’s javadoc command.

    3. Proper Indentation and Spacing
    4. Block Styles
A block is a group of statements surrounded by braces. There are two popular styles, next-line
style and end-of-line style.

next-line style
end-of-line style
public class Test
{
     public static void main(String[]args)
     {
          System.out.printlnl(“Next line style”);
     }
}
public class Test {
     public static void main(String[]args) {
          System.out.printlnl(“end of line style”);
     }
}

Preferably please use the end-of-line style
