package org.example;

import com.google.gson.GsonBuilder;
import org.example.block.Block;
import org.example.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NoobChain {
  public static int difficulty = 1;
  public static ArrayList<Block> blockchain = new ArrayList<>();

  public static void main(String[] args) {
    blockchain.add(new Block("Hi im the first block", "0"));
    System.out.println("Trying to Mine block 1... ");
    blockchain.get(0).mineBlock(difficulty);

    blockchain.add(
        new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).getHash()));
    System.out.println("Trying to Mine block 2... ");
    blockchain.get(1).mineBlock(difficulty);

    blockchain.add(
        new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).getHash()));
    System.out.println("Trying to Mine block 3... ");
    blockchain.get(2).mineBlock(difficulty);

    System.out.println("\nBlockchain is Valid: " + isChainValid());

    String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    System.out.println("\nThe block chain: ");
    System.out.println(blockchainJson);
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
