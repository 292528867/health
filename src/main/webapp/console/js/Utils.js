/**
 * Created by Jeffrey on 15/7/6.
 */

/**
 * Map
 * @constructor
 */
function Map(){

    this.data = new Array();

    this.put = function (key, value) {
        this.data[key] = value;
    };

    this.get = function (key) {
        return this.data[key];
    };

    this.isEmpty = function () {
        return this.data.length == 0;
    };

    this.size = function () {
        return this.data.length;
    }

}