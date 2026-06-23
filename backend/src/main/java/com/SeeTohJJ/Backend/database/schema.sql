create table nodes
(
    node_id          varchar(50)       not null primary key,
    topic_id         varchar(50)       not null,
    type             varchar(20)       not null,
    title            text,
    content          text,
    order_index      integer           not null,
    is_tutorial      boolean           not null,
    required_mastery integer default 0 not null,
    last_updated     timestamp         not null
);


INSERT INTO study_nodes (
    node_id,
    topic_id,
    type,
    title,
    content,
    is_tutorial,
    order_index,
    last_updated
)
VALUES

    (
        'BUD_T1',
        'BUDGETING',
        'LESSON',
        'What Is A Budget?',
        'A budget is a plan for how you will spend and save your money.',
        TRUE,
        1,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T2',
        'BUDGETING',
        'LESSON',
        'Income',
        'Income is the money you receive from work, investments, or other sources.',
        TRUE,
        2,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T3',
        'BUDGETING',
        'LESSON',
        'Expenses',
        'Expenses are costs incurred from goods and services you purchase.',
        TRUE,
        3,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T4',
        'BUDGETING',
        'QUIZ',
        'Income Quiz',
        'Which of the following is considered income?',
        TRUE,
        4,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T5',
        'BUDGETING',
        'LESSON',
        'Needs vs Wants',
        'Needs are essential for survival while wants improve quality of life.',
        TRUE,
        5,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T6',
        'BUDGETING',
        'DECISION',
        'Spending Decision',
        'You received a bonus of $500. What should you do?',
        TRUE,
        6,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T7',
        'BUDGETING',
        'LESSON',
        'Emergency Funds',
        'Emergency funds help cover unexpected expenses.',
        TRUE,
        7,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T8',
        'BUDGETING',
        'QUIZ',
        'Emergency Fund Quiz',
        'What is the primary purpose of an emergency fund?',
        TRUE,
        8,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T9',
        'BUDGETING',
        'DECISION',
        'Monthly Budget Choice',
        'You have exceeded your entertainment budget this month.',
        TRUE,
        9,
        CURRENT_TIMESTAMP
    ),

    (
        'BUD_T10',
        'BUDGETING',
        'TEST',
        'Budgeting Tutorial Test',
        'Final tutorial assessment.',
        TRUE,
        10,
        CURRENT_TIMESTAMP
    );

create table study_node_options
(
    option_id     varchar(50) primary key,
    node_id       varchar(50) not null references study_nodes(node_id) on delete cascade,
    option_text   text        not null,
    is_correct    boolean default false,
    last_updated timestamp   not null
);





