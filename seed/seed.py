"""Enters the Step 0 seed data through the API's own save endpoints.

Run it against the Part A build (git tag part-a), before rule D1 existed:
the paper's bookings on 2026-10-20 are in the past and D1 refuses those, and
D1 also forces every new booking to RESERVED. See the README.

    python seed/seed.py [http://localhost:8081]
"""
import json
import sys
import urllib.request

BASE = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8081"


def call(method, path, body=None):
    data = json.dumps(body).encode() if body is not None else None
    req = urllib.request.Request(BASE + path, data=data, method=method,
                                 headers={"Content-Type": "application/json"})
    with urllib.request.urlopen(req) as res:
        return res.read().decode()


def save(path, body):
    print(path, call("POST", f"/api/{path}/save", body))


def all_of(path):
    return json.loads(call("GET", f"/api/{path}/all"))


for name in ["Kigali - Nairobi", "Kigali - Dubai", "Kigali - Brussels", "Kigali - Johannesburg"]:
    save("routes", {"name": name})
routes = {r["name"].split(" - ")[1]: {"id": r["id"]} for r in all_of("routes")}

for number, name in [(1, "Kigali Heights Desk"), (2, "Airport Departures Desk"),
                     (3, "Town Centre Desk"), (4, "Airport Arrivals Desk")]:
    save("sales-desks", {"deskNumber": number, "name": name})
desks = {d["deskNumber"]: {"id": d["id"]} for d in all_of("sales-desks")}

save("booking-agents", {"firstName": "Claudine", "lastName": "Mukamana", "email": "claudine@belairline.rw",
                        "salesDesk": desks[1], "routes": [routes["Nairobi"], routes["Dubai"]]})
save("booking-agents", {"firstName": "Yves", "lastName": "Niyonzima", "email": "yves@belairline.rw",
                        "salesDesk": desks[2], "routes": [routes["Brussels"]]})
save("booking-agents", {"firstName": "Sandrine", "lastName": "Keza", "email": "sandrine@belairline.rw",
                        "salesDesk": desks[3],
                        "routes": [routes["Nairobi"], routes["Dubai"], routes["Brussels"]]})
agents = {a["firstName"]: {"id": a["id"]} for a in all_of("booking-agents")}

save("passengers", {"firstName": "Aline", "lastName": "Ingabire", "passportNumber": "PC123456",
                    "email": "aline.ingabire@example.com", "phone": "+250788100001"})
save("passengers", {"firstName": "Eric", "lastName": "Ingabire", "passportNumber": "PC234567",
                    "email": "eric.ingabire@example.com", "phone": "+250788100002"})
save("passengers", {"firstName": "Jean", "lastName": "Mugisha", "passportNumber": "PC345678",
                    "email": "jean.mugisha@example.com", "phone": "+250788100003"})
save("passengers", {"firstName": "Grace", "lastName": "Uwase", "passportNumber": "PC456789",
                    "email": "grace.uwase@example.com", "phone": "+250788100004"})
save("passengers", {"firstName": "Olivier", "lastName": "Habimana", "passportNumber": "PC567890",
                    "email": "olivier.habimana@example.com", "phone": "+250788100005"})
passengers = {p["passportNumber"]: {"id": p["id"]} for p in all_of("passengers")}

bookings = [
    ("2026-10-05", "TICKETED", "WB201", "Economy", "PC123456", "Claudine"),
    ("2026-10-12", "TICKETED", "WB310", "Business", "PC123456", "Yves"),
    ("2026-10-20", "CANCELLED", "WB205", "Economy", "PC234567", "Claudine"),
    ("2026-10-20", "PAID", "WB201", "Economy", "PC345678", "Claudine"),
    ("2026-10-28", "CANCELLED", "WB310", "Economy", "PC456789", "Sandrine"),
    ("2026-11-03", "RESERVED", "WB205", "Business", "PC123456", "Claudine"),
    ("2026-11-10", "PAID", "WB310", "Economy", "PC567890", "Yves"),
    ("2026-11-15", "RESERVED", "WB201", "Economy", "PC234567", "Sandrine"),
    ("2026-11-22", "TICKETED", "WB205", "Business", "PC345678", "Sandrine"),
    ("2026-11-28", "RESERVED", "WB201", "Economy", "PC456789", "Claudine"),
]
for date, status, flight, fare, passport, agent in bookings:
    save("bookings", {"travelDate": date, "status": status, "flightNumber": flight, "fareClass": fare,
                      "passenger": passengers[passport], "agent": agents[agent]})
