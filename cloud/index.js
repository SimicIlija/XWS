require('@google/cloud-debug');
let mysql = require('mysql');


/**
 * Testing sql connection, retrieving all data from cr_table table
 */
exports.testSql = function testSql(req, res) {
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'sifra',
        database: 'rating'
    });
    c.connect();
    c.query(`SELECT * FROM cr_table`, function (err, result) {
        if (err) {
            console.log("Nista ne valja");
            res.status(404).send("Something happened");
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });

};


/**
 * Getting all rattings for specific lodging
 */
exports.getLodging = function getLodging(req, res) {
    let id = req.body.idLodging;
    if (id === undefined) {
        res.status(404).send("Id is not defined");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('SELECT * FROM cr_table WHERE idLodging = ?', [id], function (error, result) {
        if (error) {
            console.log("Nista ne valja");
            res.status(404).send("Something happened");
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });
};

/**
 * Getting average grade for lodging
 */
exports.averageGrade = function getAverageGrade(req, res) {
    let id = req.body.idLodging;
    if (id === undefined) {
        res.status(404).send("Id is not defined");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('SELECT AVG(rating) FROM cr_table WHERE idLodging = ?', [id], function (error, result) {
        if (error) {
            console.log(error);
            res.status(404).send(error);
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });

};

/**
 * Get confirmed comments for specific lodging
 */
exports.confirmedComments = function confirmedComments(req, res) {
    let id = req.body.idLodging;
    if (id === undefined) {
        res.status(404).send("Id is not defined");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('SELECT * FROM cr_table WHERE idLodging = ? and confirmed = 1', [id], function (error, result) {
        if (error) {
            console.log("Nista ne valja");
            res.status(404).send("Something happened");
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });

};

/**
 * Get not verified comments
 */
exports.unconfirmedComments = function unconfirmedComments(req, res) {
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('SELECT * FROM cr_table WHERE confirmed = 0', function (error, result) {
        if (error) {
            console.log("Nista ne valja");
            res.status(404).send("Something happened");
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });
};

/**
 * Function for adding new rate and comment
 */
exports.addRateCom = function addRateCom(req, res) {
    let idLodging = req.body.idLodging;
    let idUser = req.body.idUser;
    let idReservation = req.body.idReservation;
    let rate = req.body.rating;
    let comment = req.body.comment;
    if (idLodging === undefined) {
        res.status(404).send("Id of lodging is not defined");
    }
    if (idUser === undefined) {
        res.status(404).send("Id of user is not defined");
    }
    if (idReservation === undefined) {
        res.status(404).send("Id of rate is not defined");
    }
    if (rate === undefined) {
        res.status(404).send("Rate is not defined");
    }
    if (rate < 1 || rate > 10) {
        res.status(404).send("Rate must be between 1 and 10");
    }
    if (comment === undefined) {
        res.status(404).send("Comment is not defined");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('INSERT INTO cr_table(idLodging, idUser, idReservation, rating, comment) values (?, ?, ?, ?, ?)', [idLodging, idUser, idReservation, rate, comment], function (error, result) {
        if (error) {
            console.log("Nista ne valja");
            res.status(404).send("Something happened");
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });
};

/**
 * Accepting comment by admin.
 */
exports.acceptComment = function acceptComment(req, res) {
    let idLodging = req.body.idLodging;
    let idUser = req.body.idUser;
    let idReservation = req.body.idReservation;
    if (idLodging === undefined) {
        res.status(404).send("Id of lodging is not defined");
    }
    if (idUser === undefined) {
        res.status(404).send("Id of user is not defined");
    }
    if (idReservation === undefined) {
        res.status(404).send("Id of rate is not defined");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('UPDATE cr_table SET confirmed = 1 WHERE idLodging = ? and idUser = ? and idReservation = ?',
        [idLodging, idUser, idReservation], function (error, result) {
            if (error) {
                console.log("Nista ne valja");
                res.status(404).send("Something happened");
            }
            console.log(result);
            res.status(200).send(result);
	    c.end();
        });
};

/**
 * Return ids of all lodgings, having average rate greater then input.
 * Select idlodging from cr_table group by idlodging having avg(rating)>=?
 */
exports.filterRating = function filterRating(req, res) {
    let avRate = req.body.avRate;
    if (avRate === undefined) {
        res.status(404).send("Rate not found");
    }
    let c = mysql.createConnection({
        socketPath: '/cloudsql/' + 'xml-cloud-206017:us-central1:myinstance',
        user: 'root',
        password: 'horvpuzsim',
        database: 'rating'
    });
    c.connect();
    c.query('Select idlodging from cr_table group by idlodging having avg(rating) >= ?', [avRate], function (error, result) {
        if (error) {
            console.log("Nista ne valja");
            res.status(404).send(error);
        }
        console.log(result);
        res.status(200).send(result);
        c.end();
    });
};
