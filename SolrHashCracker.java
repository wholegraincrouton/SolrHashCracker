import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SolrHashCracker {
  public static void main (String[] args) {


    if (args.length != 2) {
      System.err.println("Usage: <hashes> <wordlist>");
      System.exit(1);
    }

    String hashFile = args[0];
    String wordFile = args[1];
    String pw = "";
    String btPassEnc;
    String[] hashpair_split;
    byte[] salt = new byte[32];
    byte[] btPass;
    boolean found = false;

    try {
      BufferedReader br = new BufferedReader(new FileReader(hashFile));
      BufferedReader br2 = new BufferedReader(new FileReader(wordFile));

      for (String hashpair = br.readLine(); hashpair != null; hashpair = br.readLine()) {
        found = false;
        for (String word = br2.readLine(); word != null; word = br2.readLine()) {

          MessageDigest digest;
          try {
            digest = MessageDigest.getInstance("SHA-256");
            hashpair_split = hashpair.split(" ");

            pw = hashpair_split[0];
            salt = Base64.getDecoder().decode(hashpair_split[1]);
            
            // Pretty sure this works because reset.
            digest.reset();
            digest.update(salt);
            btPass = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            digest.reset();
            btPass = digest.digest(btPass);
            btPassEnc = Base64.getEncoder().encodeToString(btPass);

            if (btPassEnc.equals(pw)) {
              System.out.println("Found one! " +pw+ " ==> " + word);
              break;
            }

            //System.out.println(btPassEnc + " " + hashpair_split[1]);
          } catch (NoSuchAlgorithmException e) {
            System.err.println("Unknown algorithm: " + e.getMessage());
          }
        }
        if (!found) {
          System.out.println("No luck for hash pair: " + hashpair);
        }
      }
    } catch (IOException e) {
      System.out.println(e);
      System.exit(1);
    }
    System.exit(2);
  }
}
