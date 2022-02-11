public class tester 
{
    public static String textLingo(String fileName)
    {
    //get text into a string
    String text = textToString(fileName);

    // empty string for new string
    String newText = "";

    // loop through the string
    while (text.indexOf("k ") > 0 && text.length() > 0)
    {
      // look for k
      int kLoc = text.indexOf("k ");

      String isK = text.substring(0, kLoc);

      // check if text is "k + something", change to okay + something
      if (text.equals("k"))
      {
        newText += "okay";
      }

      // check if just "k", change to okay
      else if (isK.equals("k "))
      {
        newText += "okay";
      }
    // if not, keep orginal text
    }

    return newText;
  }    
}
