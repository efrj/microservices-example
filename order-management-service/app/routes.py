from flask import Blueprint, request, jsonify
from .models import db, Order, OrderItem
from .schemas import order_schema, orders_schema

orders_bp = Blueprint('orders', __name__)

@orders_bp.route('/orders', methods=['POST'])
def create_order():
    data = request.json
    new_order = Order(
        buyer_id=data['buyer_id'],
        status=data['status'],
        total_amount=data['total_amount']
    )
    db.session.add(new_order)
    db.session.flush()

    for item_data in data['items']:
        new_item = OrderItem(
            order_id=new_order.id,
            product_id=item_data['product_id'],
            quantity=item_data['quantity'],
            price=item_data['price']
        )
        db.session.add(new_item)

    db.session.commit()
    return jsonify(order_schema.dump(new_order)), 201

@orders_bp.route('/orders', methods=['GET'])
def get_orders():
    orders = Order.query.all()
    return jsonify(orders_schema.dump(orders))

@orders_bp.route('/orders/<int:order_id>', methods=['GET'])
def get_order(order_id):
    order = Order.query.get_or_404(order_id)
    return jsonify(order_schema.dump(order))

@orders_bp.route('/orders/<int:order_id>', methods=['PUT'])
def update_order_status(order_id):
    order = Order.query.get_or_404(order_id)
    data = request.json
    order.status = data['status']
    db.session.commit()
    return jsonify(order_schema.dump(order))

@orders_bp.route('/orders/<int:order_id>', methods=['DELETE'])
def cancel_order(order_id):
    order = Order.query.get_or_404(order_id)
    order.status = 'CANCELLED'
    db.session.commit()
    return jsonify(order_schema.dump(order))
