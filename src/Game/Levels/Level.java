/*
Authors:
Oscar Kardon

 */
package Game.Levels;

import Game.LevelMaker;

//Interface so all levels need to have a build method
public interface Level<Maker extends LevelMaker> {
    /**
     * Author
     * @param maker
     */
    void build(Maker maker);

}
