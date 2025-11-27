CREATE TABLE articles (
    id UUID PRIMARY KEY,
    title TEXT NOT NULL,
    subtitle TEXT,
    content_markdown TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
)