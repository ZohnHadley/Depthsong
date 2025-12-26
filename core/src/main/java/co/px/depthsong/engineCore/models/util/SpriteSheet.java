package co.px.depthsong.engineCore.models.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class SpriteSheet {

    private String title;
    private String path; // name of the file
    private Texture textureSpriteSheet;
    private int numberOfRows;
    private int numberOfColumnsPerRow;

    private int heightOfSprites;
    private int widthOfSprites;

    private ArrayList<Sprite> list_sprites;

    public SpriteSheet(String title, String path, int numberOfRows, int numberOfColumnsPerRow, int heightOfSprites, int widthOfSprites) {
        this.title = title;
        this.path = path;
        textureSpriteSheet = new Texture(this.path);

        this.numberOfRows = numberOfRows;
        this.numberOfColumnsPerRow = numberOfColumnsPerRow;
        this.heightOfSprites = heightOfSprites;
        this.widthOfSprites = widthOfSprites;

        list_sprites = new ArrayList<Sprite>();
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumnsPerRow; j++) {
                Sprite sprite = new Sprite(textureSpriteSheet, j * this.widthOfSprites, i * this.heightOfSprites, this.widthOfSprites, this.heightOfSprites);
                list_sprites.add(sprite);

            }
        }
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public Texture getTextureSpriteSheet() {
        return textureSpriteSheet;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumnsPerRow() {
        return numberOfColumnsPerRow;
    }

    public int getHeightOfSprites() {
        return heightOfSprites;
    }

    public int getWidthOfSprites() {
        return widthOfSprites;
    }

    public ArrayList<Sprite> getList_sprites() {
        return list_sprites;
    }

    public Sprite getSprite(int x, int y) {
        return list_sprites.get(y * numberOfColumnsPerRow + x);
    }
}
