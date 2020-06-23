TRUNCATE trade_goods_stock_log;
TRUNCATE trade_mq_consumer_log;
TRUNCATE trade_mq_producer_temp;
TRUNCATE trade_order;
TRUNCATE trade_pay;
TRUNCATE trade_user_money_log;

UPDATE trade_user SET user_money=1000;
UPDATE trade_goods SET goods_stock=1000;
UPDATE trade_coupon SET is_used=0,order_id=0;

SELECT * FROM trade_order;
SELECT * FROM trade_goods;
SELECT * FROM trade_coupon;
SELECT * FROM trade_goods_stock_log;