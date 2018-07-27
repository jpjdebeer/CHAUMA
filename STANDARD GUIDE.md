# STANDARD GUIDE
N.B. Please feel free to add any other standard 

These are the basic rules/standards we will adhere to. This will also help us to have a professional and easy to read code style.

<h3>1. Naming convention</h3>
<li>N.B. classes, methods, variables and constants must be descriptive and unambiguous</li>
<li>N.B. constant variable must be uppercase<li>
e.g. the days in the year:	int final DAYS_IN_YEAR = 365
<li>Pascal casing for classes</li>
Capitalize the first letter of each word in a class name
e.g. First letter of every word is uppercase
ComputerArea, Math, and JOptionPane
<li>Camel casing methods and variable identifiers</li>
   * e.g. first letter of the first word is lowercase
   *userName, accountNumber and physicalAddress
<li>Components variable names you always precede with three letters of the component</li>
e.g. button that a user clicks for sending.
btnSend
e.g. text fields that accept name and surname
txtName
txtSurname
e.g. radio buttons for yes or no
rdbYes
rdbNo
		
<h3>2. Documentation</h3>
</li>Please add comments to your methods, variables and classes and be as clear and as concise as possible</li>
<li>Include a summary at the beginning of the program to explain what the program does, its key features, and any unique techniques it uses</li>
<li>Single line comment		//text...	</li>
<li>Multiple/Paragraph comments	/*...text...*/	</li>
<li>javadoc comments. javadoc comments begin with /** and end with */. They can be extracted into an HTML file using JDK’s javadoc command. </li>

<h3>3. Proper Indentation and Spacing</h3>
<li>Format code:	Code -> Reformat code (Ctrl+Alt+L)</li>
<li>Use tabs if need be however, Android studio automatically format your code.</li>

<h3>4. Block Styles</h3>
A block is a group of statements surrounded by braces. There are two popular styles:
<li>Next-line style</li>
<li>End-of-line style</li>


```java
//This is a next-line style
public class Test
{
     public static void main(String[]args)
     {
          System.out.printlnl(“next line style”);
     }
}
```
```java
//This is an end of line style
public class Test {
     public static void main(String[]args) {
          System.out.printlnl(“end of line style”);
     }
}
```

<i>Preferably please use the end-of-line style</i>
