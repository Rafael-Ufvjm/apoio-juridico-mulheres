-- Admin inicial para desenvolvimento
-- Senha: admin123 (BCrypt)
-- TROCAR em produção via variável de ambiente ou migration separada

DO $$
DECLARE
    admin_id UUID := gen_random_uuid();
BEGIN
    INSERT INTO usuarios (id, email, senha_hash, perfil, data_cadastro, ativo)
    VALUES (
        admin_id,
        'admin@jurisapoio.com',
        '$2a$10$cCOwhtV1pEqVE8kAUSaQGe6aTnjuXgn/26dcUHTnRiIAbxQR5t4JO',
        'ADMIN',
        NOW(),
        TRUE
    );

    INSERT INTO administradores (id, nivel_acesso, two_factor_ativo)
    VALUES (admin_id, 1, FALSE);
END $$;
