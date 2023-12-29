using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    public bool isPlayer1;
    public Rigidbody2D rgb;
    public float speed;
    // Update is called once per frame

    void Update()
    {
        if (isPlayer1)
        {
        rgb.velocity = Vector2.up * Input.GetAxisRaw("Vertical") * speed;
        }
        else
        {
        rgb.velocity = Vector2.up * Input.GetAxisRaw("VerticalPlayer2") * speed;
        }


    }
}
