package co.px.depthsong.layers.models.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class SpriteSheet {

    private String title;
    private String path; // name of the file
    private Texture texture_sprite_sheet;
    private int nb_rows;
    private int nb_columns_per_row;

    private int height_of_sprites;
    private int width_of_sprites;


    private ArrayList<Sprite> list_sprites;


    public SpriteSheet(String _title, String _path, int _nb_rows, int _nb_columns_per_row, int _height_of_sprites, int _width_of_sprites) {
        title = _title;
        path = _path;
        texture_sprite_sheet = new Texture(path);

        nb_rows = _nb_rows;
        nb_columns_per_row = _nb_columns_per_row;
        height_of_sprites = _height_of_sprites;
        width_of_sprites = _width_of_sprites;
        list_sprites = new ArrayList<Sprite>();
        for (int i = 0; i < nb_rows; i++) {
            for (int j = 0; j < nb_columns_per_row; j++) {
                Sprite sprite = new Sprite(texture_sprite_sheet, j * width_of_sprites, i * height_of_sprites, width_of_sprites, height_of_sprites);
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

    public Texture getTexture_sprite_sheet() {
        return texture_sprite_sheet;
    }

    public int getNb_rows() {
        return nb_rows;
    }

    public int getNb_columns_per_row() {
        return nb_columns_per_row;
    }

    public int getHeight_of_sprites() {
        return height_of_sprites;
    }

    public int getWidth_of_sprites() {
        return width_of_sprites;
    }

    public ArrayList<Sprite> getList_sprites() {
        return list_sprites;
    }

    public Sprite getSprite(int x, int y) {
        return list_sprites.get(y * nb_columns_per_row + x);
    }
}
