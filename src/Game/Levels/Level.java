/*
Authors:
Oscar Kardon

 */
package Game.Levels;

import Game.LevelMaker;

//Generic interface so all levels, Galaga or Breakout, need to have a build method that
//can use either kind of LevelMaker
public interface Level<Maker extends LevelMaker> {
    /**
     * Author
     * @param maker
     */
    void build(Maker maker);

}
