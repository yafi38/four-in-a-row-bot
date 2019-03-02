/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package field;

/**
 * Field class
 * 
 * Field class that contains the field status data and various helper functions.
 * 
 * @author Jim van Eeden <jim@riddles.io>, Joost de Meij <joost@riddles.io>
 */

public class Field {
    private final String EMPTY_FIELD = ".";

    private int myId;
    private int width;
    private int height;
    private String[][] field;

    /**
     * Initializes and clears field
     * @throws Exception exception
     */
    public void initField() throws Exception {
        try {
            this.field = new String[this.height][this.width];
        } catch (Exception e) {
            throw new Exception("Error: trying to initialize field while field "
                    + "settings have not been parsed yet.");
        }

        clearField();
    }

	/**
	 * Clear the field
	 */
	public void clearField() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.field[i][j] = ".";
            }
        }
	}

    /**
     * Parse field from comma separated String
     * @param s input from engine
     */
    public void parseFromString(String s) {
        clearField();

        String[] split = s.split(",");
        int i = 0;
        int j = 0;

        for (String value : split) {
            this.field[i][j] = value;

            j++;
            if(j == width) {
                j = 0;
                i++;
            }
        }
    }

    public void setMyId(int id) {
        this.myId = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isValid(int i) {
        return this.field[0][i].equals(".");
    }
}
