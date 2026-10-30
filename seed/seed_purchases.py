"""Writes three ancillary purchases through E1, on bookings that are TICKETED.

Run it after seed.py, against the finished API.

    python seed/seed_purchases.py [http://localhost:8081]
"""
import json
import sys
import urllib.request

BASE = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8081"

with urllib.request.urlopen(BASE + "/api/bookings/by-status?status=TICKETED") as res:
    ticketed = {b["travelDate"]: b["id"] for b in json.load(res)}

purchases = [
    ("2026-10-05", "Extra baggage 23kg", "Paid by mobile money", 1),
    ("2026-10-05", "Lounge access", "Paid by card", 2),
    ("2026-10-12", "Extra baggage 23kg", "Paid by card", 2),
]
for date, item, note, quantity in purchases:
    body = json.dumps({"itemName": item, "note": note, "quantity": quantity}).encode()
    req = urllib.request.Request(f"{BASE}/api/ancillary-purchases/save?bookingId={ticketed[date]}", data=body,
                                 method="POST", headers={"Content-Type": "application/json"})
    with urllib.request.urlopen(req) as res:
        print(res.read().decode())
