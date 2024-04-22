package org.example;

import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.block.Block;
import org.example.util.StringUtil;
import org.example.wallet.Transaction;
import org.example.wallet.Wallet;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NoobChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 1;
    public static Wallet walletA;
    public static Wallet walletB;

  public static void main(String[] args) {
      //Setup Bouncey castle as a Security Provider
      Security.addProvider(new BouncyCastleProvider());
      //Create the new wallets
      walletA = new Wallet();
      walletB = new Wallet();
      //Test public and private keys
      System.out.println("Private and public keys:");
      System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
      System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
      //Create a test transaction from WalletA to walletB
      Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
      transaction.generateSignature(walletA.privateKey);
      //Verify the signature works and verify it from the public key
      System.out.println("Is signature verified");
      System.out.println(transaction.verifySignature());
  }

  public static Boolean isChainValid() {
    String hashTarget = StringUtil.getDifficultyString(difficulty);

    return IntStream.range(1, blockchain.size())
        .allMatch(
            blockIndex -> {
              Block currentBlock = blockchain.get(blockIndex);
              Block previousBlock = blockchain.get(blockIndex - 1);

              if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
              }

              if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("Previous Hashes not equal");
                return false;
              }

              if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
              }
              return true;
            });
  }
}
