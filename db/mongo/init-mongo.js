db.createUser({
        user: 'master',
        pwd: 'debuggeandoideas',
        roles: [
            {
                role: 'readWrite',
                db: 'testDB',
            },
        ],
    });
db.createCollection('app_users', { capped: false });

db.app_users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        //"password": "s3cr3t",
        "password":"$2a$10$AD2XHwfRJTjeYStUMljtK.pqLoQC5c/OucKT15jn/SN31OP7B60uO",
        "role":
        {
            "granted_authorities": ["read"]
        }
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        //"password": "p4sw0rd",
        "password":"$2a$10$YP0Nkqikd11i.WgkHoQ/he0NFKmjZXCLB2ax.hQG2iB69WAZtFL/K",
        "role":
        {
            "granted_authorities": ["read"]
        }
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        //"password": "misterX123",
        "password":"$2a$10$8axMhDrx.fm3a0NCqUWPl.ClwBNU6WqCaY6TJyRHxmwRlWGzeoZXa",
        "role":
        {
            "granted_authorities": ["read", "write"]
        }
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        //"password": "4dmIn",
        "password":"$2a$10$QLuFCMQsmRq2MFEjRzRyTOI86t2KNAXgC1FF8kTH1G5avTCg0q1t2",
        "role":
        {
            "granted_authorities": ["write"]
        }
    }
]);