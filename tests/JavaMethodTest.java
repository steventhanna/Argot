/**
* Remove the tag from a specified string.
* The tag must be included within the string
* @param String tag - the tag to be removed
* @param String s - the String that the tag must be removed from
* @return String - the string sans tag
* @date 1/2/12
*/
public String removeTag(String tag, String s) {
  // There should be a white space after the tag... If not the user fucked up
  // Take into account the * space
  int tagLength = tag.length() + 3;
  return s.substring(tagLength);
}
