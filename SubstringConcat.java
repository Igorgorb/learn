import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author igorg
 */
public class SubstringConcat {

  private int lengthInputString;
  private int lengthWord;
  private int countWords;
  private int lengthPhrase;
  private int lengthPhraseWithoutOne;

  public static void main(String[] args) {
    SubstringConcat sc = new SubstringConcat();
//    List<Integer> result = sc.findSubstring(s, words);
    List<Integer> result = sc.findSubstring("qwabcabcdefghida", new String[]{"abc", "def", "ghi"});
    System.out.println("result:");
    System.out.println(Arrays.toString(result.toArray()));
  }

  /*
    https://leetcode.com/problems/substring-with-concatenation-of-all-words/
  
    You are given a string s and an array of strings words of the same length. 
    Return all starting indices of substring(s) in s that is a 
    concatenation of each word in words exactly once, in any order, 
    and without any intervening characters.

  You can return the answer in any order.

    Example 1:
  Input: s = "barfoothefoobarman", words = ["foo","bar"]
  Output: [0,9]
  Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
  The output order does not matter, returning [9,0] is fine too.

    Example 2:
  Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
  Output: []

    Example 3:
  Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
  Input: s = "barfoo foobarthe foobarman",  6, words = ["bar","foo","the"]
  Input: s = "barfoofoo barthefoo barman",  9, words = ["bar","foo","the"]
  Input: s = "barfoofoobar thefoobar man", 12, words = ["bar","foo","the"]
  Output: [6,9,12] 

  Constraints:
  1 <= s.length <= 10^4
  s consists of lower-case English letters.
  1 <= words.length <= 5000
  1 <= words[i].length <= 30
  words[i] consists of lower-case English letters. 
   */
  public List<Integer> findSubstring3(String s, String[] words) {
    if (words == null
      || s == null
      || words.length == 0
      || s.length() < (words[0].length() * words.length)) {
      return Arrays.asList(new Integer[]{});
    }
    List<Integer> result = new ArrayList<>();
    List<Integer> localResult = new ArrayList<>();
// If worlds contains only one word
    if (words.length == 1) {
      int startPos = 0;
      int indexWord = s.indexOf(words[0], startPos);
      while (indexWord != -1) {
        result.add(indexWord);
        startPos = indexWord + 1;
        indexWord = s.indexOf(words[0], startPos);
      }
      return result;
    }
    // раскладываем слова по позициям в строке, потом составляем  фразы
    List<int[]> wordPos = new ArrayList<>(words.length);
    int counter = 0;
    int indexMinSize = 0;
    int minSize = Integer.MAX_VALUE;
    for (String word : words) {
      wordPos.add(getWordPositions(s, word));
      // Если слова нет, тогда не будет и фразы
      if (wordPos.get(counter).length > 0) {
        if (wordPos.get(counter).length < minSize) {
          minSize = wordPos.get(counter).length;
          indexMinSize = counter;
        }
        counter++;
      } else {
        return result;
      }
    }

// Prepare alls length, which can need 
    this.lengthInputString = s.length();
    this.lengthWord = words[0].length();
    this.countWords = words.length;
    this.lengthPhrase = words[0].length() * words.length;
    this.lengthPhraseWithoutOne = this.lengthPhrase - this.lengthWord;

    // получили позиции всех слов и определили, какого слова меньше всех, теперь составляем фразы
    // относительно позиций этих слов
    // TODO: доделать алгоритм составления фразы из слов
    return result;
  }

  private int[] getWordPositions(String s, String word) {
    List<Integer> localResult = new ArrayList<>();
    int indexWord = 0;
    indexWord = s.indexOf(word, indexWord);
    while (indexWord != -1) {
      localResult.add(indexWord);
      indexWord = s.indexOf(word, indexWord);
    }
    int[] a = localResult.stream().mapToInt(i -> i).toArray();
    Arrays.sort(a);
    return a;
//    return localResult.stream().mapToInt(i -> i).toArray();
  }

  public List<Integer> findSubstring(String s, String[] words) {

    if (words == null
      || s == null
      || words.length == 0
      || s.length() < (words[0].length() * words.length)) {
      return Arrays.asList(new Integer[]{});
    }
    List<Integer> result = new ArrayList<>();
    List<Integer> localResult = new ArrayList<>();
// If worlds contains only one word
    if (words.length == 1) {
      int startPos = 0;
      int indexWord = s.indexOf(words[0], startPos);
      while (indexWord != -1) {
        result.add(indexWord);
        startPos = indexWord + 1;
        indexWord = s.indexOf(words[0], startPos);
      }
      return result;
    }
// Prepare alls length, which can need 
    this.lengthInputString = s.length();
    this.lengthWord = words[0].length();
    this.countWords = words.length;
    this.lengthPhrase = words[0].length() * words.length;
    this.lengthPhraseWithoutOne = this.lengthPhrase - this.lengthWord;

    // positions left and right edges when may be phrase
    int posLeftEdge = 0;
    int posRightEdge = s.length() - 1;
    int startPos = 0;
    boolean isApplicable = true;
    int leftTermStartPos = 0;
    startLoop:
    while (startPos > -1) {
//      проверять слова из массива на наличие в строке, если слова из массива нет - ничего нет
      int indexWord;
      indexWord = s.indexOf(words[0], startPos);
      if (indexWord == -1) {
        // don't find - stop loop
        break;
      } else {
        // found one item - set edges window phrase
        localResult.add(indexWord);
        if (isApplicable) {
          posLeftEdge = this.getLeftEdgeForFoundWord(indexWord);
          posRightEdge = this.getRightEdgeForFoundWord(indexWord);
          if (posLeftEdge < leftTermStartPos) {
            posLeftEdge = leftTermStartPos;
          }
          startPos = posLeftEdge;
          System.out.println("indexWord - " + indexWord);
        } else {
          isApplicable = true;
        }
      }
      for (int i = 1; i < countWords; i++) {
        // TODO: сделать оптимизацию "переноса" уже найденных слов при сдвиге окна поиска
//      проверять слова из массива на наличие в строке, если слова из массива нет - ничего нет
        indexWord = s.indexOf(words[i], startPos);
        if (indexWord == -1) {
          // if don't found - stop find. It's over
          startPos = -1;
          break startLoop;
        } else {
          if (indexWord >= posLeftEdge && indexWord <= posRightEdge) {
            if (checkFoundedIndex(indexWord, localResult.get(0))) {
              // найденная позиция попадает в искомый интервал - пока все в порядке,
              // добавляем слово в копилку найденных и сужаем окно просмотра
              localResult.add(indexWord);
              if (!isFoundWindowPhrase(posLeftEdge, posRightEdge)) {
                if (indexWord > (posLeftEdge + posRightEdge) / 2) {
                  // если найденная позиция больше медианы - 
                  // значит нашли число в правой части окна и корректируем левую границу
                  posLeftEdge = getLeftEdgeForFoundWord(indexWord);
                  if (posLeftEdge < leftTermStartPos) {
                    posLeftEdge = leftTermStartPos;
                  }
                  startPos = posLeftEdge;
                } else {
                  // если найденная позиция меньше медианы - 
                  // значит нашли число в левой части окна и корректируем правую границу
                  posRightEdge = getRightEdgeForFoundWord(indexWord);
                }
              }
            } else {
              startPos = indexWord;
              posLeftEdge = indexWord;
              posRightEdge = getRightEdgeForFoundWord(indexWord);
              if (startPos > leftTermStartPos) {
                leftTermStartPos = startPos;
              }
//            else {
//              posLeftEdge = leftTermStartPos;
//              startPos = leftTermStartPos;
//            }
            }
          } else {
            startPos = getLeftEdgeForFoundWord(indexWord);
            posLeftEdge = getLeftEdgeForFoundWord(indexWord);
            posRightEdge = getRightEdgeForFoundWord(indexWord);
            if (startPos > leftTermStartPos) {
              leftTermStartPos = startPos;
            } else {
              posLeftEdge = leftTermStartPos;
              startPos = leftTermStartPos;
            }
            isApplicable = false;
            break;
          }
        }
      }
      // если массив найденных позиций совпадает по количеству 
      // с длинной искомых слов - мы нашли фразу 
      if (localResult.size() == words.length) {
        int arr[] = localResult.stream().mapToInt(i -> i).toArray();
        Arrays.sort(arr);
        result.add(arr[0]);
        startPos = arr[1];
        leftTermStartPos = startPos;
      } else {
        // мы нашли слово совсем за пределами окна и переустановили границы
        if (startPos < posRightEdge && isApplicable) {
          // могли не найти в середине фразы какое-то слово, 
          //теперь нужно определить на сколько сдвигаться, 
          //чтобы обойти пропущенное слово
          int cnt = 0;
          int arr[] = localResult.stream().mapToInt(i -> i).toArray();
          Arrays.sort(arr);
          for (int x : arr) {
            if (x != (posLeftEdge + cnt++ * lengthWord)) {
              startPos = posLeftEdge + (cnt - 1) * lengthWord;
              if (startPos < leftTermStartPos) {
                startPos = leftTermStartPos;
              }
              break;
            }
          }
          //System.out.println(Arrays.toString(localResult.toArray()));
        }
      }
      localResult.clear();
    }
    return result;
  }

  private boolean checkFoundedIndex(final int index, final int pos) {
    if (index > pos) {
      return (index - pos) % this.lengthWord == 0;
    } else {
      return (pos - index) % this.lengthWord == 0;
    }
  }

  private int getLeftEdgeForFoundWord(final int foundPosition) {
    int leftEdge = 0;
    if (foundPosition < this.lengthPhraseWithoutOne) {
      leftEdge = foundPosition - (foundPosition / this.lengthWord) * this.lengthWord;
    } else {
      leftEdge = foundPosition - (this.lengthPhraseWithoutOne);
    }
    return leftEdge;
  }

  private int getRightEdgeForFoundWord(final int foundPosition) {
    int rightEdge = 0;
    if ((foundPosition + this.lengthPhrase) > this.lengthInputString) {
      rightEdge = foundPosition + ((this.lengthInputString - foundPosition) / this.lengthWord) * this.lengthWord;
    } else {
      rightEdge = foundPosition + (this.lengthPhrase - 1);
    }
    return rightEdge;
  }

  private boolean isFoundWindowPhrase(final int leftEdge, final int rightEdge) {
    return (rightEdge - (leftEdge - 1)) == this.lengthPhrase;
  }
}
