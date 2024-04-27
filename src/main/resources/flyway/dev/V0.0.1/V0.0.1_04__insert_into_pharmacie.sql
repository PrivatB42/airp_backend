INSERT INTO public.pharmacie
(id, numero, nom, ville, quartier, heure_ouverture, heure_fermeture, nom_gerant, contact, latitude, longitude, statut,
 create_at, create_by)
VALUES (nextval('pharmacie_id_seq'), 'P000001', 'Pharmacie Sainte Famille', 'Abidjan', 'Cocody, Riviera 3', '07:30:00',
        '20:00:00', 'M. YAPI Abé', '0101010101', ' 5.3556885154656735', '-3.967334690713763', 'ACTIF', now(), 'ayapi');
