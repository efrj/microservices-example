from marshmallow import Schema, fields

class OrderItemSchema(Schema):
    id = fields.Int(dump_only=True)
    product_id = fields.Int(required=True)
    quantity = fields.Int(required=True)
    price = fields.Decimal(required=True)

class OrderSchema(Schema):
    id = fields.Int(dump_only=True)
    buyer_id = fields.Str(required=True)
    status = fields.Str(required=True)
    total_amount = fields.Decimal(required=True)
    created_at = fields.DateTime(dump_only=True)
    updated_at = fields.DateTime(dump_only=True)
    items = fields.Nested(OrderItemSchema, many=True)

order_schema = OrderSchema()
orders_schema = OrderSchema(many=True)
