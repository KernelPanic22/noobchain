package org.example.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.StringUtil;

@Data
@NoArgsConstructor

public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private Long timeStamp;

    public Block(String data, String previousHash) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }


    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash +
                        timeStamp +
                        data
        );
    }

    public void mineBlock(int difficulty) {
        String target = StringUtil.getDifficultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            timeStamp = System.currentTimeMillis();
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
